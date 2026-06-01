
import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

version = "2026.1"

project {
    vcsRoot(HttpsGithubComSpringProjectsSpringPetclinicGit)
    
    // Optional: Test 2 parameters (include if you want both)
    params {
        text("app.version", "1.0.0", label = "Application Version")
        text("environment", "development", label = "Environment")
    }
    
    buildType(BuildDummyApp)
}

object BuildDummyApp : BuildType({
    name = "Build dummy app"
    
    vcs {
        root(HttpsGithubComSpringProjectsSpringPetclinicGit)
    }
    
    // Test 3: Multiple Build Steps
    steps {
        // Step 1: Maven compile
        maven {
            name = "Maven Clean and Compile"
            id = "Maven"
            goals = "clean compile"
        }
        
        // Step 2: Show configuration
        script {
            name = "Show Build Configuration"
            id = "ShowConfig"
            scriptContent = """
                echo "========================================="
                echo "Building Application Version: %app.version%"
                echo "Environment: %environment%"
                echo "Build ID: %teamcity.build.id%"
                echo "========================================="
            """.trimIndent()
        }
        
        // Step 3: Verify build
        script {
            name = "Verify Build Artifacts"
            id = "VerifyArtifacts"
            scriptContent = """
                echo "Checking for compiled classes..."
                if [ -d target/classes ]; then
                    echo "✅ Build successful!"
                    echo "Found: $(find target/classes -name '*.class' | wc -l) class files"
                else
                    echo "❌ No compiled classes found"
                fi
            """.trimIndent()
        }
    }
})

object HttpsGithubComSpringProjectsSpringPetclinicGit : GitVcsRoot({
    name = "https://github.com/spring-projects/spring-petclinic.git"
    url = "https://github.com/spring-projects/spring-petclinic.git"
    branch = "main"
})


