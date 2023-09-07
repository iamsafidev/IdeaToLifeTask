import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.iamsafidev.ideatolifetask.R
import com.iamsafidev.ideatolifetask.domain.SquareRepo
import com.iamsafidev.ideatolifetask.presentation.UiText

@Composable
fun SquareRepoDetailScreen(squareRepoDetails: SquareRepo) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = squareRepoDetails.name,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = UiText.StringResource(R.string.stars_count, listOf(squareRepoDetails.stargazersCount)).asString(),
            color = Color.DarkGray,
            modifier = Modifier.fillMaxWidth()
        )
    }

}