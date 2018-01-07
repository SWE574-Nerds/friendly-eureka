while true; do
	git fetch --prune
	old=$(git log HEAD -1 --oneline | cut -d\  -f1)
	new=$(git log origin/master -1 --oneline | cut -d\  -f1)
	
	if [ "$old" == "$new" ]
	then 
		echo "Not deploying - $old"
		sleep 60
	fi

	if [ "$old" != "$new" ]
	then 
		echo "Deploying $new"
		git checkout master
		git pull
		/bin/bash run.sh
	fi
done
