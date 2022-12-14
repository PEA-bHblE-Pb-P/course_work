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
