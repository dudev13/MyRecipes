package com.br.infra.repository.user

import com.br.domain.entity.User
import com.br.utils.Constants
import com.br.utils.ErrorCodes
import com.mongodb.MongoException
import com.mongodb.MongoExecutionTimeoutException
import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.firstOrNull
import org.bson.types.ObjectId
import org.slf4j.LoggerFactory

class UserRepository(
    mongoDatabase: MongoDatabase
): UserReadOnlyRepository, UserWriteOnlyRepository {

    private val logger = LoggerFactory.getLogger(UserRepository::class.java)
    private val userCollection = mongoDatabase.getCollection<User>(Constants.COLLECTION_NAME_USERS)

    override suspend fun insertUser(user: User): Boolean {
        try {
            return userCollection.insertOne(user).wasAcknowledged()
        }catch (e: Exception) {
            when(e){
                is MongoException -> logger.error("${ErrorCodes.DATABASE_ERROR}: ${e.message}", e)
                else -> logger.error("${ErrorCodes.DATABASE_ERROR}: ${e.message}", e)
            }
        }
        return false
    }

    override suspend fun findUserById(userId: String): User? {
        try {
            val result = userCollection.find(Filters.eq("_id", ObjectId(userId))).firstOrNull()
            return result

        }catch (e: Exception) {
            when(e){
                is MongoException -> logger.error("${ErrorCodes.DATABASE_ERROR}: ${e.message}", e)
                else -> logger.error("${ErrorCodes.DATABASE_ERROR}: ${e.message}", e)
            }
        }
        return null
    }

    override suspend fun findUserByIds(UserIds: List<String>): List<User> {
        TODO("Not yet implemented")
    }

    override suspend fun checkIfUserExists(email: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun checkIfUserExistsReturn(email: String): User {
        TODO("Not yet implemented")
    }
}