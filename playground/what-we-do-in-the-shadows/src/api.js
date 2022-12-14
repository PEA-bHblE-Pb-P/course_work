const LOGIN = "/login";
const LOGOUT = "/logout";
const PEOPLE_NEARBY = "/people_nearby";

function endpoint(postfix) {
  return "http://localhost:8080" + postfix;
}

function endpointQuery(postfix, query) {
  return "http://localhost:8080" + postfix + "/" + query;
}

export async function login(id) {
  return await fetch(endpointQuery(LOGIN, id), {
    method: "get",
    headers: {
      Accept: "text/plain, */*",
    },
  }).then((response) => {
    if (!response.ok) throw new Error("Login error occurred!");
    else response.status;
  });
}

export async function logout() {
  return await fetch(endpoint(LOGOUT), {
    method: "get",
    headers: {
      Accept: "application/json, text/plain, */*",
    },
  }).then((response) => {
    if (!response.ok) throw new Error("Login error occurred!");
    else response.status;
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
