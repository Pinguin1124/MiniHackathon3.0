import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun GamesApp() {
    val nav = rememberNavController()
    Surface(color = MaterialTheme.colors.background) {
        NavHost(navController = nav, startDestination = "home") {
            composable("home") { HomeScreen(sampleGames(), onOpenGame = { id -> nav.navigate("game/$id") }) }
            composable("game/{gameId}") { backStackEntry ->
                val id = backStackEntry.arguments?.getString("gameId") ?: return@composable
                val game = sampleGames().firstOrNull { it.id == id } ?: return@composable
                GameDetailScreen(game = game, onLaunchMini = { miniId -> nav.navigate("mini/${game.id}/$miniId") })
            }
            composable("mini/{gameId}/{miniId}") { backStackEntry ->
                val gameId = backStackEntry.arguments?.getString("gameId") ?: return@composable
                val miniId = backStackEntry.arguments?.getString("miniId") ?: return@composable
// very simple router for 3 example mini-games
                when (miniId) {
                    "dungeon", "beast", "asteroid", "star", "crystal" -> MiniMemory()
                    "herbal", "tuning", "rune" -> MiniReaction()
                    "castle" -> MiniPuzzle()
                    else -> MiniMemory()
                }
            }
        }
    }
}