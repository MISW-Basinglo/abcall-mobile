package co.uniandes.abcall.data.repositories.issues

import co.uniandes.abcall.data.models.Issue

interface IssuesRepository {
    fun getIssues(): List<Issue>
    fun createIssue(type: String, description: String)
}
