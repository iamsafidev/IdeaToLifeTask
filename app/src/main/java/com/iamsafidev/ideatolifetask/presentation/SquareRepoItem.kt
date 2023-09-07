import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iamsafidev.ideatolifetask.domain.SquareRepo
import com.iamsafidev.ideatolifetask.ui.theme.IdeaToLifeTaskTheme

@Composable
fun SquareRepoItem(
    squareRepo: SquareRepo, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = squareRepo.name,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Stars : ${squareRepo.stargazersCount}",
                fontStyle = FontStyle.Italic,
                color = Color.DarkGray,
                modifier = Modifier.fillMaxWidth()
            )
        }

    }

}

@Preview
@Composable
fun SquareRepoItemPreview() {
    IdeaToLifeTaskTheme {
        SquareRepoItem(
            squareRepo = SquareRepo(
                id = 1,
                name = "iamsafidev",
                stargazersCount = 100
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}