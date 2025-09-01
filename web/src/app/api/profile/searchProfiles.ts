import kioke from '@/app/api';
import { MimeType } from '@/constant/mime';
import type { Profile } from '@/types/profile';
import type KiokeError from '@/util/error';
import type { Result } from 'neverthrow';

interface SearchProfilesQuery {
  query: string;
}

interface SearchProfilesResponse {
  query: string;
  profiles: {
    userId: string;
    email: string;
    name: string;
  }[];
}

function url({ query }: SearchProfilesQuery) {
  return `/users/search?query=${query}`;
}

export async function searchProfiles(
  query: SearchProfilesQuery,
): Promise<Result<Profile[], KiokeError>> {
  return kioke<SearchProfilesResponse>(url(query), {
    method: 'GET',
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  }).then((response) => response.map((data) => data.profiles));
}
