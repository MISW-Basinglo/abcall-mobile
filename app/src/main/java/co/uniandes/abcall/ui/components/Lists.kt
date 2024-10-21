package co.uniandes.abcall.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FadingLazyColumn(
    itemsCount: Int,
    itemContent: @Composable (index: Int) -> Unit,
    topGradientColor: Color = MaterialTheme.colorScheme.background,
    bottomGradientColor: Color = MaterialTheme.colorScheme.background
) {
    val listState = rememberLazyListState()
    val isAtEnd = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == itemsCount - 1

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            state = listState,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(itemsCount) { index ->
                itemContent(index)
            }
            item {
                Spacer(modifier = Modifier.height(100.dp))
            }
        }

        if (listState.firstVisibleItemScrollOffset > 0) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .height(20.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                topGradientColor,
                                Color.Transparent
                            ),
                            startY = 0f,
                            endY = 100f
                        ),
                        shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
                    )
            )
        }

        if(!isAtEnd){
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                bottomGradientColor
                            ),
                            startY = 0f,
                            endY = 100f
                        ),
                        shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                    )
            )
        }

    }
}
