package co.uniandes.abcall.data.repositories.issues

import co.uniandes.abcall.data.models.Issue

interface IssuesRepository {
    suspend fun getIssues(): List<Issue>
    suspend fun createIssue(type: String, description: String)
}
