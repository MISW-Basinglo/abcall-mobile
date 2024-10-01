package co.uniandes.abcall.data.repositories.issues

import co.uniandes.abcall.data.models.Issue
import javax.inject.Inject

class IssuesRepositoryImpl @Inject constructor(): IssuesRepository {

    override fun getIssues(): List<Issue> {

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

    override fun createIssue(type: String, description: String) {

    }

}
