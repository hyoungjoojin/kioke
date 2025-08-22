import kioke from '@/app/api';
import type { DashboardWidgetType } from '@/constant/dashboard';
import type { KiokeError } from '@/constant/error';
import { MimeType } from '@/constant/mime';
import type { Dashboard } from '@/types/dashboard';
import type { Result } from 'neverthrow';

interface GetDashboardResponse {
  widgets: {
    id: string;
    type: DashboardWidgetType;
    x: number;
    y: number;
    content: any;
  }[];
}

function url() {
  return `/dashboards/me`;
}

export async function getMyDashboard(): Promise<Result<Dashboard, KiokeError>> {
  return kioke<GetDashboardResponse>(url(), {
    method: 'GET',
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  });
}
