import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.paging.compose.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.iamsafidev.ideatolifetask.domain.SquareRepo

@Composable
fun SquareRepoScreen(
    squareRepos: LazyPagingItems<SquareRepo>,
    onNavigate: (SquareRepo) -> Unit
) {

    val context = LocalContext.current
    LaunchedEffect(key1 = squareRepos) {
        if (squareRepos.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: ${(squareRepos.loadState.refresh as LoadState.Error).error.message}",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (squareRepos.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                items(squareRepos) { squareRepo ->
                    if (squareRepo != null) {
                        SquareRepoItem(
                            squareRepo = squareRepo,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onNavigate(squareRepo)
                                }
                        )
                    }
                }

                item {
                    if (squareRepos.loadState.append is LoadState.Loading) {
                        CircularProgressIndicator()
                    }
                }

            }

        }

    }
}