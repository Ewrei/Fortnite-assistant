package robin.vitalij.fortniteassitant.network

enum class ApiError(val code: Int) {

    NO_INTERNET_ERROR(199),
    SUCCESS(200),
    CREATED(201),
    SUCCESS_DELETE(204),
    UNSUCCESS(400),
    UNAUTHORIZED(401),
    NOT_FOUND(404),
    FORBIDDEN(403),
    SERVER_ERROR(500);
}