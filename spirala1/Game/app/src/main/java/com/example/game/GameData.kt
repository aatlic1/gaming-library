package com.example.game

var lastGame : Game? = null

data class Game (
    val title: String,
    val platform: String,
    val releaseDate: String,
    val rating: Double,
    val coverImage: String,
    val esrbRating: String,
    val developer: String,
    val publisher: String,
    val genre: String,
    val description: String,
    val userImpressions: List<UserImpression>,
)

abstract class UserImpression(
    open val userName: String,
    open val timestamp: Long,
)
data class UserRating(
    override val userName: String,
    override val timestamp: Long,
    val rating: Double
):UserImpression(userName, timestamp)

data class UserReview(
    override val userName: String,
    override val timestamp: Long,
    val review: String
):UserImpression(userName, timestamp)

class GameData {
    companion object{
        fun getAll(): List<Game>{
            return listOf(
                Game("Grand Theft Auto V", "Windows PC, PlayStation 5, Xbox Series", "17.9.2013",
                    4.4, "gta", "Players mostly use pistols, machine guns, sniper rifles, and explosives to kill various enemies, players also have the ability to attack/kill non-adversary civilians, though this may negatively affect players' progress as a penalty system triggers a broad police search. Blood-splatter effects occur frequently, and the game contains rare depictions of dismemberment. In one sequence, players are directed to use various instruments and means to extract information from a character; the sequence is intense and prolonged, and involves some player interaction.",
                    "Rockstar North", "Rockstar Games", "action-adventure game", "Grand Theft Auto V is an action-adventure game played from either a third-person or first-person perspective. Players complete missions—linear scenarios with set objectives—to progress through the story. Outside of the missions, players may freely roam the open world.",
                    listOf(
                        UserRating("MINECRAFT ROBLOX ALL GAMING", 2, 5.0),
                        UserReview("Mogamad Aashieq Nackerdien", 1, "GTA 5, it is basically one of the top 5 to 10 best selling games of all time. I love GTA 5"),
                        UserRating("jaydeekayex", 1, 4.0),
                        UserReview("Deepak Singh", 2, "I don't think it's a video game that can overpower the charisma of GTA San Andreas. It's story and switching between the 3 characters may irritate you many times."),
                        UserRating("Dan Hughes", 2, 2.0),
                    )
                ),
                Game(
                    "Fortnite",
                    "Windows PC, PlayStation 4, Xbox One",
                    "21.7.2017",
                    3.0,
                    "fortnite",
                    "This is an action game in which players build forts, gather resources, craft weapons, and battle hordes of monsters in frenetic combat. From a third-person perspective, players use guns, swords, and grenades to fight skeleton-like monsters (husks) in ranged and melee-style combat. Players can also defeat enemies by using various traps. Battles are highlighted by frequent gunfire, explosions, and cries of pain.",
                    " Epic Games, People Can Fly",
                    " Epic Games, Warner Bros. Games",
                    "survival, battle royale, sandbox",
                    "Fortnite is a third person shooter survival game where the player has to survive against up to 99 other players. The total player count for each battle royale is 100. The player can build forts through collecting materials such as metal, bricks, and wood, and collect weapons before fighting other players.",
                    listOf(
                        UserReview(
                            "sword_robber2",
                            1,
                            "This game is a lot better than pubg not too much violence."
                        ),
                        UserRating("Acalodo", 1, 5.0),
                        UserReview(
                            "sdfgwg R.",
                            1,
                            "This game sucks i have 120 hz monitor and 0 ping but i still get shot through builds and my gun just blanks other players. overall -5 out of 5"
                        ),
                        UserRating("gwrocks7", 1, 1.0),
                        UserReview(
                            "Nathan H.",
                            1,
                            "Fortnite is the worst game I have ever played in my life if you don’t own a R50,000 pc or have 30 ping or less then there is no point of picking up the controller"
                        ),
                    ),
                ),
                Game(
                    "Tom Clancy's Rainbow Six Siege",
                    "Windows PC, PlayStation 4, Xbox One",
                    "1.12.2015",
                    4.0,
                    "tcrss",
                    "This is a first-person shooter in which players control members of an elite counter-terrorism unit through various missions. Game modes allow players to eliminate terrorist cells, defuse bombs, and extract hostages. Characters use firearms (e.g., pistols, machine guns, shotguns), explosives, and traps (e.g., electrified barbed wire, robots with tasers) to kill human enemies.",
                    "Ubisoft Montreal",
                    "Ubisoft",
                    "tactical shooter",
                    "Tom Clancy's Rainbow Six Siege is a first-person shooter game, in which players utilize many different operators from the Rainbow team. Different operators have different nationalities, weapons, and gadgets.",
                    listOf(
                        UserReview(
                            "Chud Maverick",
                            3,
                            "I own it on Uplay, glad Ubisoft stuck by this title after a rocky launch. I play on and off during the weeks, its one of my go-to games, the quintessential hardcore fps with cool gadgets and destruction to-boot."
                        ),
                        UserRating("Dean Smith", 2, 3.0),
                        UserReview(
                            "Joseph Pocknell",
                            3,
                            "While rainbow six siege is a game in which you will rage a lot, it is amazing, I have spent many hours on it and while I don’t consider myself a ‘god’ on this game"
                        ),
                    ),
                ),
                Game(
                    "Apex Legends",
                    "Windows PC, PlayStation 4, Xbox One, Xbox Series",
                    "4.2.2019",
                    4.3,
                    "apex",
                    "This is a first-person shooter in which players engage in squad-based battle royale-style combat across a large island. Players search for supplies and use machine guns, rifles, and explosives to shoot and kill enemies as the play area constantly shrinks. Frenetic firefights are accompanied by blood-splatter effects, realistic gunfire, and large explosions.",
                    " Respawn Entertainment, Panic Button Games",
                    "Electronic Arts",
                    "first-person shooter, battle royale game",
                    "Apex Legends is an online multiplayer battle royale game featuring squads of three players using pre-made characters with distinctive abilities, called \"Legends\", similar to those of hero shooters. Alternate modes have been introduced allowing for single and for two-player squads since the game's release.",
                    listOf(
                        UserRating("Val4567", 1, 3.0),
                        UserReview(
                            "a.smith123",
                            1,
                            "I think this game is really good for my sons, they both enjoy it a lot and as for games chat I have when though there settings and changed it to my approval."
                        ),
                    ),
                ),
                Game(
                    "Rocket League",
                    "Windows PC, PlayStation 4, Xbox One",
                    "7.7.2015",
                    3.8,
                    "rocket",
                    "This is an action-racing game in which players drive futuristic cars to play games of soccer. Players attempt to score the most goals as they compete in a variety of game modes. One song contains lyrics that reference tobacco.",
                    "Corey Davis",
                    "Psyonix",
                    "action game, sports video game",
                    "Rocket League is a fantastical sport-based video game, developed by Psyonix. It features a competitive game mode based on teamwork and outmaneuvering opponents. Players work with their team to advance the ball down the field, and score goals in their opponents' net.",
                    listOf(
                        UserRating("Zie Teniere", 1, 1.0),
                        UserReview(
                            "Thomas Ryan",
                            1,
                            "Play 1 here. Rocket league is a game I recommend for anyone who loves competitive video games."
                        ),
                    ),
                ),
                Game(
                    "League of Legends",
                    "Windows PC",
                    "27.10.2009",
                    3.0,
                    "league",
                    "This is a real-time strategy game (RTS) in which players control otherworldly warriors in combat. Players earn experience for their characters by battling enemy minions in matches. The game is rated Teen because of the depictions of red blood splashes emitted from characters during battle.",
                    "Riot Games",
                    "Riot Games",
                    "multiplayer online battle arena",
                    "League of Legends is one of the world's most popular video games, developed by Riot Games. It features a team-based competitive game mode based on strategy and outplaying opponents.",
                    listOf(
                        UserRating("Lucius Walker", 3, 4.0),
                    ),
                ),
                Game(
                    "Destiny 2",
                    "PlayStation 5, PlayStation 4, Xbox One, Xbox Series X and Series S, Google Stadia, Microsoft Windows",
                    "6.9.2017",
                    3.9,
                    "destiny",
                    "This is a first-person shooter in which players assume the role of a group (Guardians) defending humanity against an alien force. Players travel across futuristic landscapes and planets to battle a horde of alien creatures. Players use pistols, machine guns, rocket launchers, and various elemental weapons to kill enemies.",
                    "Bungie Inc.",
                    "Activision",
                    "massively multiplayer online game",
                    "Similar to its predecessor, Destiny 2 is a first-person shooter game that incorporates role-playing and massively multiplayer online game (MMO) elements. The original Destiny includes on-the-fly matchmaking that allowed players to communicate only with other players with whom they were \"matched\" by the game.",
                    listOf(
                        UserReview(
                            "Braden Huang",
                            3,
                            "It’s one of the best games I have ever played easily, and I have played tons of games in my life including Elder Scrolls, all the Call of Dutys, Halo, the sims, legend of Zelda, etc."
                        ),
                    ),
                ),
                Game(
                    "Halo Infinite",
                    "Windows PC, Xbox One, Xbox Series",
                    "15.11.2021",
                    4.1,
                    "halo",
                    "This is a first-person shooter in which players engage in a variety of multiplayer events on large-scale, futuristic battlefields. Players use firearms, grenades, and laser swords to kill opponents in head-to-head battle. Combat is highlighted by explosions, frequent gunfire, and cries of pain. Human characters emit small puffs of blood when injured. The words “a*s” and “p*ssed” appear in the game.",
                    "343 Industries",
                    "Xbox Game Studios",
                    "first-person shooter",
                    "Halo Infinite is a first-person shooter akin to past Halo games. Players use weapons and vehicles commonly found in the Halo series, such as the Warthog.",
                    listOf(),
                ),
                Game(
                    "The Sims 4",
                    "PlayStation 4, Xbox One, Mac operating systems, Microsoft Windows",
                    "2.9.2014",
                    4.0,
                    "sims",
                    "This is a “sandbox” simulation game in which players create, customize, and control characters called “Sims” through their daily activities. Players are free to pursue a variety of daily goals as they observe and attempt to influence other Sims in town. They sometimes vomit or urinate on themselves if their hygiene is not properly managed.",
                    "Maxis, The Sims Studio",
                    "Electronic Arts",
                    "life simulation game, free-to-play",
                    "The Sims 4 is a social simulation game developed by Maxis and published by Electronic Arts. It is the fourth major title in The Sims series, and is the sequel to The Sims 3.",
                    listOf(
                        UserReview(
                            "Pascale Jarjoura",
                            1,
                            "In my opinion this game is the best on many levels, it's both entertaining and educational, and it suits a wide range of ages."
                        ),
                        UserRating("J.K. Young (Anon)", 1, 5.0),
                    ),
                ),
                Game(
                    "Forza Horizon 5",
                    "Windows PC, Xbox One, Xbox Series",
                    "5.11.2021",
                    4.2,
                    "forza",
                    "This is a racing simulation game in which players assume the role of a veteran driver helping to expand the Horizon Festival to Mexico. Players can compete in a variety of racing events to earn accolades and upgrades.",
                    "Playground Games",
                    "Xbox Game Studios",
                    "racing video game, adventure game, action game",
                    "Forza Horizon 5 is a 2021 racing video game developed by Playground Games and published by Xbox Game Studios. It is the fifth Forza Horizon title and twelfth main instalment in the Forza series. The game is set in a fictionalised representation of Mexico.",
                    listOf(),
                ),
            )

        }
        fun getDetails(title:String): Game{
            val games: List<Game> = getAll()
            val game= games.find { game -> title == game.title }
            return game?:Game("","","",0.0,"","", "", "", "", "", listOf());
        }
    }
}