const LOGIN = "/login";
const LOGOUT = "/logout";
const PEOPLE_NEARBY = "/people_nearby";
const GO_TO_LOCATION_ID = "/go_to_location_by_id"

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
    if (!response.ok) throw new Error("Logout error occurred!");
    else response.status;
  });
}

export async function people_nearby() {
  return fetch(endpoint(PEOPLE_NEARBY), {
    method: "get",
    headers: {
      Accept: "application/json, text/plain, */*",
    }
  }).then((response) => {
    if (!response.ok) throw new Error("People nearby error occurred!");
    else response.json();
  });
}

export async function go_to_location_id(id) {
  return await fetch(endpointQuery(GO_TO_LOCATION_ID, id), {
    method: "get",
    headers: {
      Accept: "application/json, text/plain, */*",
    },
  }).then((response) => {
    if (!response.ok) throw new Error("Go to error occurred!");
    else response.status;
  });
}