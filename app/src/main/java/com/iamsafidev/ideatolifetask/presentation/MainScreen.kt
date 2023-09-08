import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.iamsafidev.ideatolifetask.presentation.SquareRepoViewModel

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "square_repos"
    ) {
        composable("square_repos") {
            val viewModel = hiltViewModel<SquareRepoViewModel>()
            val squareRepos = viewModel.getSquareReposPagingFlow().collectAsLazyPagingItems()

            SquareRepoScreen(
                squareRepos = squareRepos,
                onNavigate = {
                    viewModel.setSelectionState(it)
                    navController.navigate("square_repos_detail")
                }
            )
        }
        composable("square_repos_detail") {
            val viewModel = hiltViewModel<SquareRepoViewModel>()
            val state by viewModel.sharedState.collectAsStateWithLifecycle()

            state?.let {
                SquareRepoDetailScreen(
                    squareRepoDetails = it
                )
            }
        }
    }
}