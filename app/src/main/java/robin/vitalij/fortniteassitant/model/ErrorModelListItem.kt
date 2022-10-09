package robin.vitalij.fortniteassitant.model

sealed class ErrorModelListItem {

    data class ErrorItem(val errorModel: ErrorModel): ErrorModelListItem()

    data class MessageItem(val message: String): ErrorModelListItem()

}