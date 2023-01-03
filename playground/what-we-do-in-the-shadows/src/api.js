const LOGIN = "/login";
const LOGOUT = "/logout";
const PEOPLE_NEARBY = "/people_nearby";
const GO_TO_LOCATION_ID = "/go_to_location_by_id"
const CHARACTER_ME = "/character/me"

function endpoint(postfix) {
  return "http://localhost:8080" + postfix;
}

function endpointQuery(postfix, query) {
  return "http://localhost:8080" + postfix + "/" + query;
}

export async function login(id) {
  return await fetch(endpointQuery(LOGIN, id), {
    method: "post",
    credentials: "include",
    headers: {
      Accept: "text/plain, */*",
      'Access-Control-Allow-Origin': "localhost",
      'Access-Control-Allow-Credentials': true
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

export async function character() {
  console.log(document.cookie)
  return await fetch(endpoint(CHARACTER_ME), {
    method: "get",
    credentials: "include",
    headers: {
      Accept: "application/json, */*",
      'Allow-Origin': "*",
      'Access-Control-Allow-Origin': "*",
      'Access-Control-Allow-Credentials': true
    },
  }).then((response) => {
    if (!response.ok) throw new Error("Login error occurred!");
    else response.json();
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