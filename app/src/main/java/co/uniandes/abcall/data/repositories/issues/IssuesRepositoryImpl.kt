package co.uniandes.abcall.data.repositories.issues

import co.uniandes.abcall.data.models.Issue
import co.uniandes.abcall.networking.AbcallApi
import co.uniandes.abcall.networking.IssueRequest
import co.uniandes.abcall.networking.SuggestRequest
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

    override suspend fun suggestIssue(description: String): String {
        //api.suggestIssue(SuggestRequest(description))
        return "Al completar el campo de descripción, asegúrate de incluir información clara y detallada sobre el incidente. Describe el problema específico que estás enfrentando, incluyendo pasos que llevaron al inconveniente, cualquier mensaje de error que hayas recibido y el impacto que tiene en tu actividad. Cuanta más información proporciones, más fácil será para nuestro equipo entender y resolver tu solicitud de manera eficiente."
    }

}
