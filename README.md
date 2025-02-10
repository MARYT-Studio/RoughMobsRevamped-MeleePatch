# ImprovedMobs Melee Patch

Very simple and small mod.

Patch ImprovedMobs to let it deal with "ranged melee attacks" like SlashBlades properly.

---------------------
## Development / Contribution 

This mod project utilize [TemplateDevEnv](https://github.com/CleanroomMC/TemplateDevEnv),

which runs on Java 21 and utilize **Gradle 8.12** + **[RetroFuturaGradle](https://github.com/GTNewHorizons/RetroFuturaGradle) 1.4.1** + **Forge 14.23.5.2847**.

### "How to" for devs

1. Click `use this template` at the top.
2. Clone the repository that you have created with this template to your local machine.
3. Make sure IDEA is using Java 21 for Gradle before you sync the project. Verify this by going to IDEA's `Settings > Build, Execution, Deployment > Build Tools > Gradle > Gradle JVM`.
4. Open the project folder in IDEA. When prompted, click "Load Gradle Project" as it detects the `build.gradle`, if you weren't prompted, right-click the project's `build.gradle` in IDEA, select `Link Gradle Project`, after completion, hit `Refresh All` in the gradle tab on the right.
5. Run gradle tasks such as `runClient` and `runServer` in the IDEA gradle tab, or use the auto-imported run configurations like `1. Run Client`.

The latest [MinecraftDev Fork for RetroFuturaGradle](https://github.com/eigenraven/MinecraftDev/releases) plugin is recommended for IntelliJ users.
