package robin.vitalij.fortniteassitant.utils.mapper

import robin.vitalij.fortniteassitant.db.entity.UserEntity
import robin.vitalij.fortniteassitant.ui.users.adapter.UserModel
import robin.vitalij.fortniteassitant.utils.mapper.base.Mapper

class UsersMapper(private val playerId: String) :
    Mapper<List<UserEntity>, List<UserModel>> {

    override fun transform(obj: List<UserEntity>): List<UserModel> {
        val list = arrayListOf<UserModel>()
        obj.filter { it.all?.overall?.matches != 0 }.forEach {
            list.add(UserModel(it, it.playerId == playerId))
        }
        return list
    }
}