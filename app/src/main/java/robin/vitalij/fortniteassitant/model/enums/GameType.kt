package robin.vitalij.fortniteassitant.model.enums

import robin.vitalij.fortniteassitant.R

enum class GameType(val id: Int) {

    ALL(0) {
        override fun getTitleRes() = R.string.all
        override fun getImageRes() = R.drawable.ic_all
        override fun getServer() = "all"
    },
    KEYBOARD_MOUSE(1) {
        override fun getTitleRes() = R.string.keyboard_mouse
        override fun getImageRes() = R.drawable.ic_keyboard
        override fun getServer() = "keyboardMouse"
    },
    GAMEPAD(2) {
        override fun getTitleRes() = R.string.gamepad
        override fun getImageRes() = R.drawable.ic_gamepad
        override fun getServer() = "gamepad"
    },
    TOUCH(3) {
        override fun getTitleRes() = R.string.touch
        override fun getImageRes() = R.drawable.ic_touch
        override fun getServer() = "touch"
    };

    abstract fun getServer(): String
    abstract fun getTitleRes(): Int
    abstract fun getImageRes(): Int
}
