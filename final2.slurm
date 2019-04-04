#!/bin/bash

#SBATCH --nodes=1
#SBATCH --output=log/mysort2GB.log
#SBATCH --wait-all-nodes=1

srun ./run2GB.sh $SLURM_JOB_NODELIST

