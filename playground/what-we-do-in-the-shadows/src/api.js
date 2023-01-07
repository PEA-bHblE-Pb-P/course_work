const LOGIN = "/login";
const LOGOUT = "/logout";
const PEOPLE_NEARBY = "/character/nearby";
const GO_TO_LOCATION_ID = "/go_to_location_by_id";
const CHARACTER_ME = "/character/me";
const LOCATION_BY_ID = "/location";
const GO_FOR_FIGHT = "/hunter_go_to_for_fight"

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
      Accept: "application/json, text/plain, */*",
      "Access-Control-Allow-Origin": "localhost",
      "Access-Control-Allow-Credentials": true,
    },
  }).then((response) => {
    if (!response.ok) throw new Error("Login error occurred!");
    else response.status;
  });
}

export async function logout() {
  return await fetch(endpoint(LOGOUT), {
    method: "post",
    credentials: "include",
    headers: {
      Accept: "application/json, text/plain, */*",
      "Access-Control-Allow-Origin": "localhost",
      "Access-Control-Allow-Credentials": true,
    },
  }).then((response) => {
    if (!response.ok) throw new Error("Logout error occurred!");
    else response.status;
  });
}

export async function character() {
  const resp = await fetch(endpoint(CHARACTER_ME), {
    method: "get",
    credentials: "include",
    headers: {
      Accept: "application/json, */*",
      "Access-Control-Allow-Origin": "*",
      "Access-Control-Allow-Credentials": true,
    },
  });

  if (!resp.ok) throw new Error("Character error occurred!");

  return await resp.json();
}

export async function people_nearby() {
  const resp = await fetch(endpoint(PEOPLE_NEARBY), {
    method: "get",
    credentials: "include",
    headers: {
      Accept: "application/json, text/plain, */*",
      "Access-Control-Allow-Origin": "localhost",
      "Access-Control-Allow-Credentials": true,
    },
  });

  if (!resp.ok) throw new Error("People nearby error occurred!");

  return await resp.json();
}

export async function go_to_location_id(id) {
  return await fetch(endpointQuery(GO_TO_LOCATION_ID, id), {
    method: "post",
    credentials: "include",
    headers: {
      Accept: "application/json, text/plain, */*",
      "Access-Control-Allow-Origin": "localhost",
      "Access-Control-Allow-Credentials": true,
    },
  }).then((response) => {
    if (!response.ok) throw new Error("Go to error occurred!");
    else response.status;
  });
}

export async function go_for_fight(id) {
  return await fetch(endpointQuery(GO_FOR_FIGHT, id), {
    method: "post",
    credentials: "include",
    headers: {
      Accept: "application/json, text/plain, */*",
      "Access-Control-Allow-Origin": "localhost",
      "Access-Control-Allow-Credentials": true,
    },
  }).then((response) => {
    if (!response.ok) throw new Error("Go to error occurred!");
    else response.status;
  });
}

export async function get_location(id) {
  const resp = await fetch(endpointQuery(LOCATION_BY_ID, id), {
    method: "get",
    credentials: "include",
    headers: {
      Accept: "application/json, text/plain, */*",
      "Access-Control-Allow-Origin": "localhost",
      "Access-Control-Allow-Credentials": true,
    },
  });

  if (!resp.ok) throw new Error("Location error occurred!");

  return await resp.json();
}
