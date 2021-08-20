package robin.vitalij.fortniteassitant.interfaces

interface SaveUserCallback {

    fun showOrHideProgressBar(isVisible: Boolean)

    fun showError(throwable: Throwable)

    fun showMessage(title: String)

    fun done()

}