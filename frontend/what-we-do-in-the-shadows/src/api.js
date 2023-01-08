const LOGIN = "/login";
const LOGOUT = "/logout";
const PEOPLE_NEARBY = "/character/nearby";
const CHARACTER_ME = "/character/me";
const LOCATION_BY_ID = "/location";
const GO_FOR_FIGHT = "/hunter_go_to_for_fight"
const LOCATION_ALL = "/location/all"
const CHARACTERS = "/character/all"
const DRINK_BLOOD = "/drink_blood"

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
      Accept: "*/*",
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

export async function characters() {
  const resp = await fetch(endpoint(CHARACTERS), {
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
  const resp = await fetch(endpointQuery(LOCATION_BY_ID, id)+"/go", {
    method: "post",
    credentials: "include",
    headers: {
      Accept: "application/json, text/plain, */*",
      "Access-Control-Allow-Origin": "localhost",
      "Access-Control-Allow-Credentials": true,
    },
  });

  if (!resp.ok) throw new Error("Go to location error occurred!");
  return resp.status;
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

export async function locations() {
  const resp = await fetch(endpoint(LOCATION_ALL), {
    method: "get",
    credentials: "include",
    headers: {
      Accept: "application/json, text/plain, */*",
      "Access-Control-Allow-Origin": "localhost",
      "Access-Control-Allow-Credentials": true,
    },
  });

  if (!resp.ok) throw new Error("Location count error occurred!");
  return await resp.json();
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

export async function drink(char, amount) {
  return await fetch(endpoint(DRINK_BLOOD), {
    method: "post",
    credentials: "include",
    headers: {
      Accept: "application/json, text/plain, */*",
      "Access-Control-Allow-Origin": "localhost",
      "Access-Control-Allow-Credentials": true,
      "Content-type": "application/json; charset=UTF-8"
    },
    body: JSON.stringify({
      charId: char,
      amount: amount,
    }),
  }).then((response) => {
    if (!response.ok) throw new Error("Login error occurred!");
    else response.status;
  });
}