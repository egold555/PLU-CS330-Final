# CS330 Homedepot AI Final Project
*Description of the project goes here*

**This project is only built for Windows at the moment!**

## Table of Contents
- [CS330 Homedepot AI Final Project](#cs330-homedepot-ai-final-project)
  - [Table of Contents](#table-of-contents)
  - [Team Members](#team-members)
  - [Example Videos](#example-videos)
  - [Installing \& Running](#installing--running)
    - [Installing](#installing)
    - [Running](#running)
  - [Downloading the models](#downloading-the-models)
    - [Models to clone](#models-to-clone)
    - [Downloading](#downloading)
    - [Converting to FP16](#converting-to-fp16)
    - [Converting to q4\_0](#converting-to-q4_0)
  - [Results \& Summary](#results--summary)
    - [GPT 3.5 Turbo](#gpt-35-turbo)
    - [GPT 4](#gpt-4)
    - [LLAMA 7B](#llama-7b)
    - [LLAMA 13B](#llama-13b)
    - [Wizard 13B](#wizard-13b)
    - [Orca 2 7B](#orca-2-7b)
    - [Orca 2 13B](#orca-2-13b)
    - [Openhermes 2.5 mistral 7B](#openhermes-25-mistral-7b)
    - [What I learned](#what-i-learned)
  - [Basic Troubleshooting](#basic-troubleshooting)

## Team Members
- Eric Golde

## Example Videos
This project takes a lot of disk space, and can be a pain to get to compile properly. These videos are provided to showcase how the project works for each of the given models for convenience.
| Model Name    | Example Video                                                                                                                               |
| ------------- | ------------------------------------------------------------------------------------------------------------------------------------------- |
| GPT 3.5 Turbo | [![GPT 3.5 Example](https://markdown-videos-api.jorgenkh.no/url?url=https%3A%2F%2Fyoutu.be%2FEVEnqtYl0vY)](https://youtu.be/EVEnqtYl0vY)    |
| GPT 4         | [![GPT 4 Example](https://markdown-videos-api.jorgenkh.no/url?url=https%3A%2F%2Fyoutu.be%2FN0SuDh7I9uM)](https://youtu.be/N0SuDh7I9uM)      |
| LLAMA 7B      | [![LLAMA 7b Example](https://markdown-videos-api.jorgenkh.no/url?url=https%3A%2F%2Fyoutu.be%2FsvyEbFeL6C0)](https://youtu.be/svyEbFeL6C0)   |
| LLAMA 13B     | [![LLAMA 13b Example](https://markdown-videos-api.jorgenkh.no/url?url=https%3A%2F%2Fyoutu.be%2FWrUIHKu7LLc)](https://youtu.be/WrUIHKu7LLc)  |
| Wizard 13B    | [![Wizard 13b Example](https://markdown-videos-api.jorgenkh.no/url?url=https%3A%2F%2Fyoutu.be%2FWrUIHKu7LLc)](https://youtu.be/WrUIHKu7LLc) |
| Orca 2 7B     | [![Orca 2 7b Example](https://markdown-videos-api.jorgenkh.no/url?url=https%3A%2F%2Fyoutu.be%2FcCSpiXtpkEE)](https://youtu.be/cCSpiXtpkEE)  |
| Orca 2 13B    | [![Orca 2 13b Example](https://markdown-videos-api.jorgenkh.no/url?url=https%3A%2F%2Fyoutu.be%2FFvGlrgcHDdA)](https://youtu.be/FvGlrgcHDdA) |
| Openhermes 2.5 mistral 7b   | [![Openhermes 2.5 mistral 7b](https://markdown-videos-api.jorgenkh.no/url?url=TBA)](TBA) |


## Installing & Running
### Installing
1. Clone this repo
2. In the llama.cpp folder:
    1. Clone [the repo](https://github.com/ggerganov/llama.cpp/tree/b1483)
    2. Build it following the build instructions on the repo (Don't forget to enable GPU!)
3. In the models folder:
    1. Clone [all the models you want to use](#downloading-the-models)
    2. Convert your models to fp16 using the [steps below](#converting-to-fp16)
    3. Convert your fp16 to q4_0 using the [steps below](#converting-to-q4_0)
    4. If you are using GPT 3.5 or 4, you need to put your API key in the `api_key.txt` file in the `models/GPT` folder
4. Import the project into IntelliJ

### Running
1. Run the project
2. Find two products on homedepot.com, or use the ones pre-populated in the search bar
3. Click the search button
4. Wait for the results to come in
5. Select the AI you want to use in the settings panel
6. Change any other settings you want per model (Default settings work well for most models)
7. Click the start button
8. Wait for the magic to happen


## Downloading the models
### Models to clone
- [GPT 3.5 & 4](https://need-to-put-a-link-here)
- [LLAMA 7B, 13B & 70B](https://github.com/facebookresearch/llama)
- [Wizard Mega 13B](https://huggingface.co/openaccess-ai-collective/wizard-mega-13b)
- [Orca 2 7B](https://huggingface.co/microsoft/Orca-2-7b)
- [Orca 2 13B](https://huggingface.co/microsoft/Orca-2-13b)
- [Openhermes 2.5 mistral 7b](https://huggingface.co/teknium/OpenHermes-2.5-Mistral-7B)

### Downloading
In git terminal
```bash
git lfs install
git clone <link to repo>
```

### Converting to FP16
In Linux for windows subsystem

```bash
python3 -m pip install -r requirements.txt
python3 convert.py models/<model name>/
```

### Converting to q4_0
In windows terminal

```bash
./quantize ./models/<model name>/ggml-model-f16.gguf ./models/<model name>/ggml-model-q4_0.gguf q4_0
```

Now you can delete all the files in the models folder except for the q4_0 model

## Results & Summary

### GPT 3.5 Turbo
GPT 3.5 Turbo worked very well at this task. It was able to give very detailed answers, and was very good at staying on topic.

This was the fastest model to run.

### GPT 4
GPT 4 worked very well at this task. It was able to give very detailed answers, and was very good at staying on topic, but it was sometimes overly verbose in its descriptions.

It was also slow, but not as slow as the local models.

### LLAMA 7B
LLAMA 7B did not work well at all. It either would make things up, or just talk about random facts and ignore any user input.

### LLAMA 13B
LLAMA 13B did not work well at all. It either would make things up, or just talk about random facts and ignore any user input. I did not see a difference between LLAMA 13B and LLAMA 7B.

### Wizard 13B
Wizard 13B worked very well at this task. It was able to give very detailed answers, and was very good at staying on topic.

### Orca 2 7B
Orca 2 7B worked very well at this task. It was able to give very detailed answers, and was very good at staying on topic.

Orca 2 was the fastest local model to run.

### Orca 2 13B
Orca 2 13B worked very well at this task. It was able to give very detailed answers, and was very good at staying on topic. I can't tell the difference between Orca 2 7B and Orca 2 13B.

### Openhermes 2.5 mistral 7B
This model worked, but it did not give bullet points and a summary. It just gave a summary of both products which is not exactly what it was asked to do. Other than that it worked well.

### What I learned
- GPT 3.5 Turbo is the best and fastest model for this task
- Orca 2 is the best local model for this task
- Every LLAMA model is bad at this task
- **This is not a very simple task for a AI to accomplish, dispite being a simple task on paper.**


## Basic Troubleshooting
- If the program crashes or hangs, you may need to kill the process `main.exe` in task manager.
- If llama.cpp fails to run, try running it outside of IntelliJ with the command `main.exe` in the `llama.cpp` folder.
- If the model fails to convert to fp16 due to a token error, try editing the model settings in the models json file, to specify the correct tokens.
