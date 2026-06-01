import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2026.1"

project {

    vcsRoot(HttpsGithubComSpringProjectsSpringPetclinicGit)

    buildType(BuildDummyApp)
}

object BuildDummyApp : BuildType({
    name = "App controlled by git"

    vcs {
        root(HttpsGithubComSpringProjectsSpringPetclinicGit)
    }

    steps {
        maven {
            name = "Maven"
            id = "Maven"
            goals = "clean compile"
        }
    }
})

object HttpsGithubComSpringProjectsSpringPetclinicGit : GitVcsRoot({
    name = "https://github.com/spring-projects/spring-petclinic.git"
    url = "https://github.com/spring-projects/spring-petclinic.git"
    branch = "main"
})
