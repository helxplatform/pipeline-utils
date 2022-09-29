def ccv(gitCredsUsr, gitCredsPsw) {
    withEnv(["GIT_CREDS_USR=gitCredsUsr", "GIT_CREDS_PSW=gitCredsPsw"]) {
        sh '''
        git clone https://github.com/helxplatform/helx-ui.git
        cd helx-ui/
        go install github.com/smlx/ccv@v0.3.2
        cat <<EOF > ccv.sh
        #!/bin/bash
        if [ -z $(git tag -l $(ccv)) ]; then
            git tag $(ccv)
            git tag   # testing
            git remote set origin https://${GIT_CREDS_USR}:${GIT_CREDS_PSW}@github.com/helxplatform/helx-ui.git
            # git push --tags
            # git push origin --tags
        fi
        EOF
        chmod +x ccv.sh
        ./ccv.sh
        '''
    }
}