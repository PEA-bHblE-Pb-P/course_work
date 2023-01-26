export function mapLocationType(type) {
  switch (type) {
    case 1:
      return "bar";
    case 2:
      return "living";
    case 3:
      return "cafe";
    case 4:
      return "hospital";
    default:
      return undefined;
  }
}

export function mapCharacterType(type) {
  switch (type) {
    case 1:
      return "вампир";
    case 2:
      return "оборотень";
    case 3:
      return "человек";
    case 4:
      return "энергетический вампир";
    case 5:
      return "охотник на вампиров";
    default:
      return "человек";
  }
}

export function isHunterType(type) {
  return type === 5;
}

export function typedLocation(loc) {
  return {
    id: loc.id,
    name: loc.name,
    lat: loc.lat,
    lon: loc.lon,
    type: mapLocationType(loc.locationTypeId),
    photoUrl: loc.photoUrl,
    vampiresCount: loc.vampiresCount,
  };
}
function getAge(dateString) {
  const today = new Date();
  const birthDate = new Date(dateString);
  let age = today.getFullYear() - birthDate.getFullYear();
  const m = today.getMonth() - birthDate.getMonth();
  if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
    age--;
  }
  return age;
}

function ageByBirthday(birthday) {
  const age = getAge(birthday);

  if (age < 20) {
    return "young";
  }

  if (age < 45) {
    return "cougar";
  }

  return "old";
}

function mapHumanImage(sex, birthday) {
  switch (sex) {
    case "masculine":
      return "/" + ageByBirthday(birthday) + "_man.png";
    case "feminine":
      return "/" + ageByBirthday(birthday) + "_woman.png";
    default:
      return "/undefined.png";
  }
}

export function mapImage(character) {
  switch (character.typeId) {
    case 1:
      return "/вампир.jpg";
    case 2:
      return "/оборотень.png";
    case 3:
      return mapHumanImage(character.sex, character.birthday);
    case 4:
      return "/вампир.png";
    case 5:
      return "/охотник.png";
    default:
      return "/undefined.png";
  }
}
