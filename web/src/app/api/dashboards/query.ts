import {
  type UpdateDashboardRequestBody,
  getMyDashboard,
  updateDashboard,
} from '.';
import { unwrap } from '@/util/result';
import { useMutation, useQuery } from '@tanstack/react-query';

const getMyDashboardQueryKey = ['dashboard', 'my'];

const getMyDashboardQueryOptions = {
  queryKey: getMyDashboardQueryKey,
  queryFn: async () => getMyDashboard().then((res) => unwrap(res)),
};

function useGetMyDashboardQuery() {
  return useQuery({
    ...getMyDashboardQueryOptions,
  });
}

function useUpdateDashboardMutation() {
  return useMutation({
    mutationFn: async (params: UpdateDashboardRequestBody) =>
      updateDashboard({
        body: params,
      }).then((res) => unwrap(res)),
  });
}

export {
  useGetMyDashboardQuery,
  getMyDashboardQueryKey,
  useUpdateDashboardMutation,
};
