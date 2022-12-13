const LOGIN = "/login";
const LOGOUT = "/logout";
const PEOPLE_NEARBY = "/people_nearby";

function endpoint(postfix) {
  return "http://localhost:8080" + postfix;
}

function endpointQuery(postfix, query) {
  return "http://localhost:8080" + postfix + "/" + query;
}

export function login(id) {
  return fetch(endpointQuery(LOGIN, id), {
    method: "get",
    headers: {
      Accept: "text/plain, */*",
      "Access-Control-Allow-Origin": "*",
      "Access-Control-Allow-Headers": "*",
      "Content-Type": "application/json",
    },
  }).then((response) => {
    if (!response.ok) throw new Error("Login error occurred!");
    else return response.json();
  });
}

async function logout() {
  return fetch(endpoint(LOGOUT), {
    method: "post",
    headers: {
      Accept: "application/json, text/plain, */*",
      "Content-Type": "application/json",
    },
  });
}

async function people_nearby() {
  return fetch(endpoint(PEOPLE_NEARBY), {
    method: "get",
    headers: {
      Accept: "application/json, text/plain, */*",
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      id: id,
    }),
  });
}
