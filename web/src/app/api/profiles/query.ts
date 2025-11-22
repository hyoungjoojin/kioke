import { type UpdateProfileParams, getMyProfile, updateProfile } from '.';
import { unwrap } from '@/util/result';
import { useMutation, useQuery } from '@tanstack/react-query';

const getMyProfileQueryOptions = {
  queryKey: ['profiles', 'me'],
  queryFn: async () => getMyProfile().then((res) => unwrap(res)),
};

function useGetMyProfileQuery() {
  return useQuery({
    ...getMyProfileQueryOptions,
  });
}

function useUpdateProfileMutation() {
  return useMutation({
    mutationFn: async (params: UpdateProfileParams) =>
      updateProfile(params).then((res) => unwrap(res)),
  });
}

export {
  useGetMyProfileQuery,
  getMyProfileQueryOptions,
  useUpdateProfileMutation,
};
