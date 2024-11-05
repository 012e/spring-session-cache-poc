import http from "k6/http";
import { sleep, check } from "k6";

const url = "http://localhost:8080";

export const options = {
  // A number specifying the number of VUs to run concurrently.
  vus: 20,
  // A string specifying the total duration of the test run.
  duration: "60s",

  // The following section contains configuration options for execution of this
  // test script in Grafana Cloud.
  //
  // See https://grafana.com/docs/grafana-cloud/k6/get-started/run-cloud-tests-from-the-cli/
  // to learn about authoring and running k6 test scripts in Grafana k6 Cloud.
  //
  // cloud: {
  //   // The ID of the project to which the test is assigned in the k6 Cloud UI.
  //   // By default tests are executed in default project.
  //   projectID: "",
  //   // The name of the test in the k6 Cloud UI.
  //   // Test runs with the same name will be grouped.
  //   name: "script.js"
  // },

  // Uncomment this section to enable the use of Browser API in your tests.
  //
  // See https://grafana.com/docs/k6/latest/using-k6-browser/running-browser-tests/ to learn more
  // about using Browser API in your test scripts.
  //
  // scenarios: {
  //   // The scenario name appears in the result summary, tags, and so on.
  //   // You can give the scenario any name, as long as each name in the script is unique.
  //   ui: {
  //     // Executor is a mandatory parameter for browser-based tests.
  //     // Shared iterations in this case tells k6 to reuse VUs to execute iterations.
  //     //
  //     // See https://grafana.com/docs/k6/latest/using-k6/scenarios/executors/ for other executor types.
  //     executor: 'shared-iterations',
  //     options: {
  //       browser: {
  //         // This is a mandatory parameter that instructs k6 to launch and
  //         // connect to a chromium-based browser, and use it to run UI-based
  //         // tests.
  //         type: 'chromium',
  //       },
  //     },
  //   },
  // }
};

function generateRandomString(minLength, maxLength) {
  // Define the characters to choose from (alphabets and numbers)
  const characters =
    "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

  // Generate a random length between minLength and maxLength
  const length =
    Math.floor(Math.random() * (maxLength - minLength + 1)) + minLength;

  // Initialize the result string
  let result = "";

  // Loop to generate a string of the chosen length
  for (let i = 0; i < length; i++) {
    const randomIndex = Math.floor(Math.random() * characters.length);
    result += characters[randomIndex];
  }

  return result;
}

function randomNumber(min, max) {
  return Math.floor(Math.random() * (max - min + 1)) + min;
}

const jsonParam = {
  headers: {
    "Content-Type": "application/json",
  },
};

export default function () {
  const username = generateRandomString(5, 10);
  const password = generateRandomString(10, 20);

  const loginData = JSON.stringify({
    username: username,
    password: password,
  });

  const registerData = JSON.stringify({
    firstName: "a",
    lastName: "b",
    username: username,
    password: password,
  });

  let registerResult = http.post(
    `${url}/auth/register`,
    registerData,
    jsonParam,
  );
  check(registerResult, {
    "register response is 200": (r) => r.status === 200,
  });

  let loginResult = http.post(`${url}/auth/login`, loginData, jsonParam);
  check(loginResult, {
    "login response is 200": (r) => r.status === 200,
  });

  const totalSecretRequests = randomNumber(1, 30);
  for (let i = 0; i < totalSecretRequests; i++) {
    let secretResult = http.get(`${url}/secret`);
    check(secretResult, {
      "secret response is 200": (r) => r.status === 200,
      "secret data is correct": (r) =>
        r.body ===
        JSON.stringify({ success: true, message: "Secret data", data: null }),
    });
  }
}
