import ky from "ky";

export const kioke = ky.create({
  prefixUrl: process.env.KIOKE_BACKEND_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

export const protectedKioke = (accessToken: string) => {
  return kioke.extend({
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  });
};
