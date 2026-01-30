from fastapi import FastAPI
import time

app = FastAPI()

@app.post("/compute")
def compute(data: dict):
    start = time.time()

    # Simulate heavy computation
    result = sum(data["numbers"])

    end = time.time()

    return {
        "result": result,
        "execution_time": end - start
    }
