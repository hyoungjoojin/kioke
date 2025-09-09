import {
  type SendFriendRequest,
  sendFriendRequest,
} from '@/app/api/friend/sendFriendRequest';
import type KiokeError from '@/util/error';
import { unwrap } from '@/util/result';
import { useMutation } from '@tanstack/react-query';

type TData = void;
type TError = KiokeError;
type TVariables = SendFriendRequest;
type TContext = void;

function useSendFriendRequestMutation() {
  return useMutation<TData, TError, TVariables, TContext>({
    mutationFn: async (requestBody) =>
      sendFriendRequest(requestBody).then((result) => unwrap(result)),
  });
}

export { useSendFriendRequestMutation };
