import whisper
import sys
import warnings

# 🔥 remove warnings
warnings.filterwarnings("ignore")

# 🔥 load BASE model instead of tiny
model = whisper.load_model("base")

audio_path = sys.argv[1]

result = model.transcribe(audio_path)

# 🔥 print ONLY transcript
print(result["text"])