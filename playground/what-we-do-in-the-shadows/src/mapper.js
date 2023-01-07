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