import kioke from '@/app/api';
import type { DashboardWidgetType } from '@/constant/dashboard';
import { MimeType } from '@/constant/mime';
import type { Dashboard } from '@/types/dashboard';
import type KiokeError from '@/util/error';
import type { Result } from 'neverthrow';

interface GetDashboardPathParams {
  userId: string;
}

interface GetDashboardResponse {
  widgets: {
    id: string;
    type: DashboardWidgetType;
    x: number;
    y: number;
    content: any;
  }[];
}

function url({ userId }: GetDashboardPathParams) {
  return `/dashboards/${userId}`;
}

export async function getDashboard(
  pathParams: GetDashboardPathParams,
): Promise<Result<Dashboard, KiokeError>> {
  return kioke<GetDashboardResponse>(url(pathParams), {
    method: 'GET',
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  });
}

export async function getMyDashboard(): Promise<Result<Dashboard, KiokeError>> {
  return kioke<GetDashboardResponse>(
    url({
      userId: 'me',
    }),
    {
      method: 'GET',
      headers: {
        'Content-Type': MimeType.APPLICATION_JSON,
      },
    },
  );
}
