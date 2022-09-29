def ccv(githubToken) {
    withEnv(["GITHUB_TOKEN=githubToken"]) {
        sh '''
        git clone https://github.com/helxplatform/helx-ui.git
        cd helx-ui/
        go install github.com/smlx/ccv@v0.3.2
        cat <<EOF > ccv.sh
        #!/bin/bash
        if [ -z $(git tag -l $(ccv)) ]; then
            git tag $(ccv)
            git tag   # testing
            git remote set origin https://${GITHUB_TOKEN}@github.com/helxplatform/helx-ui.git
            # git push --tags
            # git push origin --tags
        fi
        EOF
        chmod +x ccv.sh
        ./ccv.sh
        '''
    }
}