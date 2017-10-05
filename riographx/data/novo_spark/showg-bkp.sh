#!/bin/bash

workdir="/usr/lib/riographx"
cd "$workdir/"

while read tline; do

	IFS=':' read -r -a groups <<< "$tline"


	for i in "${groups[@]}" do
    
		IFS=',()][' read -r -a array <<< "$i"

		rddkey=${array[0]}
		index_id=${array[1]}
		function=${array[2]}
		g6=${array[3]}
		ordem=${array[4]}
		grauminimo=${array[5]}
		graumaximo=${array[6]}
		trianglefree=${array[7]}
		conexo=${array[8]}
		bipartite=${array[9]}
		parameter_id=${array[10]}
		caixa1=${array[11]}
		adjacency=${array[12]}
		laplacian=${array[13]}
		slaplacian=${array[14]}
		allowdiscgraphs=${array[15]}
		biptonly=${array[16]}
		maxresults=${array[17]}
		adjacencyb=${array[18]}
		laplacianb=${array[19]}
		slaplacianb=${array[20]}
		chromatic=${array[21]}
		chromaticb=${array[22]}
		click=${array[23]}
		clickb=${array[24]}
		largestdegree=${array[25]}
		numedges=${array[26]}
		runGeni=${array[27]}
		runEigsolve=${array[28]}
		serial=${array[29]}
		order_min=${array[30]}
		order_max=${array[31]}

		functionResult=${array[32]}
	
		echo "Confere funcao:  $function e resultado: $functionResult "

	done

done

