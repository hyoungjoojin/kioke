import kioke from '@/app/api';
import type { DashboardWidgetType } from '@/constant/dashboard';
import type { KiokeError } from '@/constant/error';
import type { Dashboard } from '@/types/dashboard';
import type { Result } from 'neverthrow';

interface GetDashboardResponseBody {
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
  return kioke<GetDashboardResponseBody>(url(), {
    method: 'GET',
  });
}
