package ru.mugss

import android.graphics.drawable.Drawable
import androidx.compose.ui.res.stringResource
import ru.mugss.ui.model.Playlist
import ru.mugss.ui.model.SongModel

object Constants {
    const val normalMode = "Обычный"
    const val animeMode = "Аниме"
    const val unknownNameOfSong = "???"
    const val unknownAuthorOfSong  = "?"
    val list = listOf<Playlist>(
        Playlist(normalMode, R.drawable.ic_normal_mode), Playlist(
            animeMode, R.drawable.ic_anime_mode
        )
    )
    const val durationOfSong = 30000L
    val listOfSongs = listOf(
        SongModel(
            name = "My Beautiful Dark Twisted Fantasy",
            author = "Kanye West",
            urlImage = "https://i.scdn.co/image/ab67616d00001e02d9194aa18fa4c9362b47464f",
            urlSong = "https://p.scdn.co/mp3-preview/217208d09f5cdea4610222fa754078135feec957?cid=774b29d4f13844c495f206cafdad9c86"
        ),
        SongModel(
            name = "Oh Messy Life",
            author = "Cap'n Jazz",
            urlImage = "https://i.scdn.co/image/ab67616d00001e021042c88a974fed41972c0f77",
            urlSong = "https://p.scdn.co/mp3-preview/f6dd7d550c8f6f590a7efc49baad6711c4e05f68?cid=774b29d4f13844c495f206cafdad9c86"
        ),
        SongModel(
            name = "Freaks",
            author = "Surf Curse",
            urlImage = "https://i.scdn.co/image/ab67616d00001e029efda673310de265a2c1cf1f",
            urlSong = "https://p.scdn.co/mp3-preview/63aca5dd766d1f3601324054fb82de05dce4a6a7?cid=774b29d4f13844c495f206cafdad9c86"
        ),
        SongModel(
            name = "Street Spirit (Fade Out)",
            author = "Radiohead",
            urlImage = "https://i.scdn.co/image/ab67616d00001e029293c743fa542094336c5e12",
            urlSong = "https://p.scdn.co/mp3-preview/0b36d0fcb313fc8aa35223c319864a52b8b14b99?cid=774b29d4f13844c495f206cafdad9c86"
        ),
        SongModel(
            name = "Остаться настоящим",
            author = "kizaru",
            urlImage = "https://i.scdn.co/image/ab67616d00001e02e9611449192f3e9e8fc70ad4",
            urlSong = "https://p.scdn.co/mp3-preview/619fb305129cc32c3c5662917713eb8668083021?cid=774b29d4f13844c495f206cafdad9c86"
        ),
        SongModel(
            name = "On A Roll",
            author = "Balthazar",
            urlImage = "https://i.scdn.co/image/ab67616d00001e02e0aaf7b46564fce9139ab598",
            urlSong = "https://p.scdn.co/mp3-preview/bc96c01625fb8402dac0366e05c3e258320c9ba4?cid=774b29d4f13844c495f206cafdad9c86"
        ),
        SongModel(
            name = "Sextape",
            author = "Deftones",
            urlImage = "https://i.scdn.co/image/ab67616d00001e0272c2145d48f68917a7361b15",
            urlSong = "https://p.scdn.co/mp3-preview/f204178ac4e9a4f071626d9ed5c5c60ffc2fdbb2?cid=774b29d4f13844c495f206cafdad9c86"
        ),
        SongModel(
            name = "Где ты",
            author = "Три дня дождя",
            urlImage = "https://i.scdn.co/image/ab67616d00001e024202da6f60a9bc8c6c3d1559",
            urlSong = "https://p.scdn.co/mp3-preview/d6570f28fd158613de5e5fd7995211eed5a7bef0?cid=774b29d4f13844c495f206cafdad9c86"
        ),
        SongModel(
            name = "Ну и что, что я вор?",
            author = "СЕРЕГА ПИРАТ",
            urlImage = "https://i.scdn.co/image/ab67616d00001e027e9d67b726331b8e47cabde8",
            urlSong = "https://p.scdn.co/mp3-preview/b92454cf7471be24c90d90ab20a103e2a96fd84b?cid=774b29d4f13844c495f206cafdad9c86"
        ),
        SongModel(
            name = "Ginseng Strip 2002",
            author = "Yung Lean",
            urlImage = "https://i.scdn.co/image/ab67616d00001e02581915272a4ffd12551c0202",
            urlSong = "https://p.scdn.co/mp3-preview/a3622dfe56dfc0b5ca9c23ca5e46c0b2ac49ddb4?cid=774b29d4f13844c495f206cafdad9c86"
        ),
        SongModel(
            name = "Tell Me Why (Berserk)",
            author = "Miura Jam",
            urlImage = "https://i.scdn.co/image/ab67616d00001e02a6cf6c9447b12234c9ed0df7",
            urlSong = "https://p.scdn.co/mp3-preview/d6f91af8e76c15581b2ebf045e9c0a7cd01fcb42?cid=774b29d4f13844c495f206cafdad9c86"
        ),
        SongModel(
            name = "Walk",
            author = "Kwabs",
            urlImage = "https://i.scdn.co/image/ab67616d00001e0234aa4f801bcff0cda1e806d9",
            urlSong = "https://p.scdn.co/mp3-preview/ff5f45023160038e5ad3a9703238317e52fa5299?cid=774b29d4f13844c495f206cafdad9c86"
        ),
        SongModel(
            name = "We Are Young (feat. Janelle Monáe)",
            author = "fun.",
            urlImage = "https://i.scdn.co/image/ab67616d00001e02a036e1724bc7f2bab15cfda8",
            urlSong = "https://p.scdn.co/mp3-preview/71c8730f77d94b6d4bcbb6bfcfec6233ad8d1509?cid=774b29d4f13844c495f206cafdad9c86"
        ),
        SongModel(
            name = "Mr. Brightside",
            author = "The Killers",
            urlImage = "https://i.scdn.co/image/ab67616d00001e02ccdddd46119a4ff53eaf1f5d",
            urlSong = "https://p.scdn.co/mp3-preview/ae6de4873b51e32c32063554c0aaee384d328057?cid=774b29d4f13844c495f206cafdad9c86"
        ),
        SongModel(
            name = "Creep",
            author = "Radiohead",
            urlImage = "https://i.scdn.co/image/ab67616d00001e02df55e326ed144ab4f5cecf95",
            urlSong = "https://p.scdn.co/mp3-preview/8665eb54a981f3c2e8d575d1c0392ff743d54b97?cid=774b29d4f13844c495f206cafdad9c86"
        ),
        SongModel(
            name = "The Place Where He Inserted the Blade",
            author = "Black Country, New Road",
            urlImage = "https://i.scdn.co/image/ab67616d00001e02af2d88c14cb157d6b851dbb3",
            urlSong = "https://p.scdn.co/mp3-preview/ddfbe896eae02d531df7aaa1030641fece3a83a1?cid=774b29d4f13844c495f206cafdad9c86"
        ),
        SongModel(
            name = "Chamber Of Reflection",
            author = "Mac DeMarco",
            urlImage = "https://i.scdn.co/image/ab67616d00001e02ec6e9c13eeed14eedbd5f7c9",
            urlSong = "https://p.scdn.co/mp3-preview/91a369dc9320bf9c26ddd9269e1d189f4d849b71?cid=774b29d4f13844c495f206cafdad9c86"
        ),
        SongModel(
            name = "Newlove",
            author = "Sewerslvt",
            urlImage = "https://i.scdn.co/image/ab67616d00001e0234e34d3368a26e72a9629ed4",
            urlSong = "https://p.scdn.co/mp3-preview/0a1876765f220eeaa99bdf3afbec9c7f9eca53e8?cid=774b29d4f13844c495f206cafdad9c86"
        ),
        SongModel(
            name = "Ночь",
            author = "Andrey Gubin",
            urlImage = "https://i.scdn.co/image/ab67616d00001e02d29f75c5866560dc79330caa",
            urlSong = "https://p.scdn.co/mp3-preview/3a653f75ba78d9c87a72db820805374d6f4c7b7c?cid=774b29d4f13844c495f206cafdad9c86"
        ),
        SongModel(
            name = "когда?",
            author = "источник",
            urlImage = "https://i.scdn.co/image/ab67616d00001e02d4dcbd90e20948d23e4e13bd",
            urlSong = "https://p.scdn.co/mp3-preview/83c9a61137a73df2db43f642e373ecf9297f1714?cid=774b29d4f13844c495f206cafdad9c86"
        ),
        SongModel(
            name = "Мы сегодня дома",
            author = "Хадн дадн",
            urlImage = "https://i.scdn.co/image/ab67616d00001e020942030d4569de78f68a9b8a",
            urlSong = "https://p.scdn.co/mp3-preview/e01eb740798ddf5c0d412f9e0df392159ba214d9?cid=774b29d4f13844c495f206cafdad9c86"
        ),
        SongModel(
            name = "Somebody That I Used To Know",
            author = "Gotye",
            urlImage = "https://i.scdn.co/image/ab67616d00001e028ac5768205ad97df3f4f4c0e",
            urlSong = "https://p.scdn.co/mp3-preview/3513d9aa00e0ae889ca47c60b936fddfaee06f6d?cid=774b29d4f13844c495f206cafdad9c86"
        ),
        SongModel(
            name = "Порох",
            author = "Zamay",
            urlImage = "https://i.scdn.co/image/ab67616d00001e0277f5c030ef9127d6c8a0851f",
            urlSong = "https://p.scdn.co/mp3-preview/e55017ccd9339fed4e8805fcc58247bc584d9dfa?cid=774b29d4f13844c495f206cafdad9c86"
        ),
        SongModel(
            name = "Never Meant",
            author = "American Football",
            urlImage = "https://i.scdn.co/image/ab67616d00001e02d9194aa18fa4c9362b47464f",
            urlSong = "https://p.scdn.co/mp3-preview/5b3a77a15ddf6ee239e5d62bb1fb672ab6b89537?cid=774b29d4f13844c495f206cafdad9c86"
        ),
        SongModel(
            name = "Mayonaka no Door / Stay With Me",
            author = "Miki Matsubara",
            urlImage = "https://i.scdn.co/image/ab67616d00001e0281052badd62d5e14c3377786",
            urlSong = "https://p.scdn.co/mp3-preview/71db1277abac5652307cdf478a422b5c8a3e4864?cid=774b29d4f13844c495f206cafdad9c86"
        ),
        SongModel(
            name = "Лучший город Земли",
            author = "Муслим Магомаев",
            urlImage = "https://i.scdn.co/image/ab67616d00001e02f8a08fb01c4d0c534f1bd1ac",
            urlSong = "https://p.scdn.co/mp3-preview/f3cd751293b68327b38eb6297effeecf5517c2b1?cid=774b29d4f13844c495f206cafdad9c86"
        ),
        SongModel(
            name = "Money",
            author = "The Drums",
            urlImage = "https://i.scdn.co/image/ab67616d00001e024c27d14a19e67dac23661031",
            urlSong = "https://p.scdn.co/mp3-preview/a4455d76a828c2e0a6ca978d246aebd7a0839714?cid=774b29d4f13844c495f206cafdad9c86"
        ),
        SongModel(
            name = "What You Know",
            author = "Two Door Cinema Club",
            urlImage = "https://i.scdn.co/image/ab67616d00001e02635eb2cbc738455671d199db",
            urlSong = "https://p.scdn.co/mp3-preview/66d55b58bc94ecf31afa776e5ecd6e7977a4ff9c?cid=774b29d4f13844c495f206cafdad9c86"
        ),
        SongModel(
            name = "Полярная звезда",
            author = "Мы",
            urlImage = "https://i.scdn.co/image/ab67616d00001e0222f6119310c53bfc73ca1716",
            urlSong = "https://p.scdn.co/mp3-preview/1f6c1f6eb3776c5c26ec943b048034aa99e25e6c?cid=774b29d4f13844c495f206cafdad9c86"
        ),
        SongModel(
            name = "Midnight City",
            author = "M83",
            urlImage = "https://i.scdn.co/image/ab67616d00001e02b3591763154a27326b3f431a",
            urlSong = "https://p.scdn.co/mp3-preview/8dd33976a29b409d92ed33cc50d76e84ffd96079?cid=774b29d4f13844c495f206cafdad9c86"
        ),
    )
}