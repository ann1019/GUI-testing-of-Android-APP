#!/bin/bash
rm log.txt
	
	if [ $1 != "lab1.apk" ]
	then
		echo "lab1"
	fi
	
	rm -rf layout
	rm -rf waiting
	rm -rf testing
	rm -rf bugreport
	rm -rf reverse
	rm -rf tempscript
	rm -rf result
	#======================
	rm -rf layout_$3/
	rm -rf bugreport_$3/
	rm -rf comparebug/

	mkdir layout
	mkdir waiting
	mkdir testing
	mkdir bugreport
	mkdir tempscript
	mkdir result
	#=======================
	mkdir layout_$3/
	mkdir bugreport_$3/
	mkdir comparebug/

	start_time=$(date +%s)
	start_time1=$(date +%Y-%m-%d_%T)
	adb install $1
	count=0
	#java -jar Ripper.jar $1
	java -jar simpleRipper.jar $1 $3
	#$count = 0
	#echo "我有出來了！"
	#if [ $1 != "lab1.apk" ]
	#then
	#	while [ $count < 3 ] #算waiting資料夾內有多少檔案
		while [ $[`ls -1 ./waiting |wc -l`] -ne 0 ] #算waiting資料夾內有多少檔案
		do
			waitingList=(`find ./waiting`) #算waiting資料夾內有什麼檔案有什麼資料夾 是array
			echo "waitinglist: ${waitifngList[1]}"
			filename=(`basename ${waitifngList[1]}`) #Get the file name
			if [ -e ./testing/$filename ]||[ test -e ./result/$filename ] #folder result maybe be deleted 
				then	
					rm ${waitingList[1]}  #delete the data
				else
					java -jar Ripper.jar $1 ${waitingList[1]} $3
					(( count++ ))
					echo $count >> count.txt
					if [ $[`ls -1 ./tempscript |wc -l`] -ne 0 ] #如果tempscrip資料夾有東西
					then					
						mv -n ./tempscript/* ./waiting
						mv ${waitingList[1]} ./testing
					else
						mv ${waitingList[1]} ./result
					fi
			fi #end if
			#$count=$count+1
		done
	#fi
	end_time=$(date +%s)
	diff_time=$[ $end_time - $start_time ]
	echo "APP_NAME:" >> log.txt
	echo $1 >> log.txt
	echo "START_TIME:" >> log.txt
	echo $start_time1 >> log.txt
	echo "times: " >> log.txt
	echo $i >> log.txt
	echo "diff_time:" >> log.txt
	echo $diff_time >> log.txt
	echo "UI-state number:" >> log.txt
	echo $[`ls -1 ./testing |wc -l`]+$[`ls -1 ./result |wc -l`] >> log.txt
	echo "======================================================" >> log.txt
	
	cp -r layout/. layout_$3/
	cp -r bugreport/. bugreport_$3/
