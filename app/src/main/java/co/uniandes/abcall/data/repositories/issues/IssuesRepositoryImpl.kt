package co.uniandes.abcall.data.repositories.issues

import co.uniandes.abcall.data.models.Issue
import co.uniandes.abcall.networking.AbcallApi
import co.uniandes.abcall.networking.IssueRequest
import javax.inject.Inject

class IssuesRepositoryImpl @Inject constructor(
    private val api: AbcallApi
): IssuesRepository {

    override suspend fun getIssues(): List<Issue> {
        //return api.getIssues()
        return listOf(
            Issue(
                "Título 1",
                "Lorem ipsum dolor sit amet consectetur adipiscing elit turpis, porta litora risus penatibus curabitur et enim egestas...",
                "29/02/2024 18:50:33",
                "Cerrado"
            ),
            Issue(
                "Título 2",
                "Lorem ipsum dolor sit amet consectetur adipiscing elit turpis, porta litora risus penatibus curabitur et enim egestas...",
                "29/02/2024 18:50:33",
                "Escalado"
            ),
            Issue(
                "Título 3",
                "Lorem ipsum dolor sit amet consectetur adipiscing elit turpis, porta litora risus penatibus curabitur et enim egestas...",
                "29/02/2024 18:50:33",
                "Abierto"
            )
        )
    }

    override suspend fun createIssue(type: String, description: String) {
        //api.createIssue(IssueRequest(type, description))
    }

}
