## Compile

make all

########### Sorting ###########

<workload type (2 and 20 GB)>
<number of threads (1)>
###### Compile #####

make all

########### Sorting ###########

1.Run Makefile
2.Run the Final.slurm and final2.slurm file with command "sbatch <Filename>
3.It will run the run2GB.sh and run20GB.sh file with all the input parameters.
4.Refer the output file for the out put as required.

########### Linsort Benchmark ###########

1. run sort --parallel=2 /input/data-2GB.in --output= Data>>out1.log
2. run sort --parallel=2 /input/data-20GB.in --output= Data>>out1.log