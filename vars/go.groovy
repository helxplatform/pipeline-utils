def ccv() {
    sh '''
    go get github.com/smlx/ccv
    cat <<EOF > ccv.sh
    #!/bin/bash
    if [ -z $(git tag -l $(ccv)) ]; then
	    git tag $(ccv)
        git tag   # testing
        # git push --tags
    fi
    EOF
    chmod +x ccv.sh
    ./ccv.sh
    '''
}