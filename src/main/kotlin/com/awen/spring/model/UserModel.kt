package com.awen.spring.model

import com.awen.spring.repository.entity.UserEntity

class UserModel: BaseModel<UserEntity>() {

    var account: String = ""
    var password: String = ""
    var realName: String? = null
    var disable: Int = 0

    override fun initModel(entity: UserEntity) {
        account = entity.account
        password = entity.password
        realName = entity.realName
        disable = entity.disable
    }


    override fun toEntity(): UserEntity {
        val entity = UserEntity(
                account, password, realName, disable
        )

        return initBaseEntity(entity);
    }
}