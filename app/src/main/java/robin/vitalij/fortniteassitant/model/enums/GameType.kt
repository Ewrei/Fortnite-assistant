package robin.vitalij.fortniteassitant.model.enums

import robin.vitalij.fortniteassitant.R

enum class GameType(val id: Int) {

    ALL(0) {
        override fun getTitleRes() = R.string.all
        override fun getImageRes() = R.drawable.ic_all
    },
    KEYBOARD_MOUSE(1) {
        override fun getTitleRes() = R.string.all
        override fun getImageRes() = R.drawable.ic_desktop
    },
    GAMEPAD(2) {
        override fun getTitleRes() = R.string.all
        override fun getImageRes() = R.drawable.ic_console
    },
    TOUCH(3) {
        override fun getTitleRes() = R.string.all
        override fun getImageRes() = R.drawable.ic_ps4
    };

    abstract fun getTitleRes(): Int
    abstract fun getImageRes(): Int
}
