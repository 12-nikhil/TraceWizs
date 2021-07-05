package com.softwise.tracewizs.fragment;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.softwise.tracewizs.R;
import com.softwise.tracewizs.listeners.fragmentview.IProgressView;
import com.softwise.tracewizs.models.AllGateEntry;
import com.softwise.tracewizs.models.Material;
import com.softwise.tracewizs.models.ServerResponse;
import com.softwise.tracewizs.serverUtils.ApiClients;
import com.softwise.tracewizs.serverUtils.ServiceListeners.APIService;
import com.softwise.tracewizs.utils.SPTRaceWizsConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.softwise.tracewizs.helper.MethodHelper.getTokenFromSp;
import static com.softwise.tracewizs.utils.TraceWizConstant.CONTENT_TYPE;
import static com.softwise.tracewizs.utils.TraceWizConstant.RAW_MATERIAL;

import static com.softwise.tracewizs.utils.TraceWizConstant.SELECT_UNIT;
import static com.softwise.tracewizs.utils.TraceWizConstant.UNAUTHORISED;


public class MaterialTypeFragment extends GateEntryBaseFragment implements IProgressView {
    public String SELECT_MATERIAL = "Select Material";
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.spn_material)
    Spinner spnMaterial;
    @BindView(R.id.spn_unit)
    Spinner spnUnit;
    @BindView(R.id.edt_qty)
    EditText edtQty;
    @BindView(R.id.rb_raw)
    RadioButton rbRaw;
    @BindView(R.id.rb_package)
    RadioButton rbPackage;
    AllGateEntry mAllGateEntry;
    private String TAG = "MaterialTypeFragment";
    private String selectedMaterialName;
    private String selectedUnit;
    private Material mMaterial;

    public MaterialTypeFragment(AllGateEntry allGateEntry) {
        // Required empty public constructor
        mAllGateEntry = allGateEntry;
    }

    @Override
    protected Fragment getFragment() {
        return this;
    }

    @Override
    protected void start() {
        mGateEntryPresenter.setGateEntryFragmentView(this);
        mGateEntryPresenter.setUid(SPTRaceWizsConstants.getUserId(getActivity()));
        clickListeners();

    }

    @Override
    public void onResume() {
        super.onResume();
        loadMaterialFields();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_material_type;
    }


    @Override
    protected boolean isFormValid() {
        if (mAllGateEntry == null) {
            if (selectedMaterialName == null || SELECT_MATERIAL.equals(selectedMaterialName)) {
                Toast.makeText(getActivity(), getString(R.string.msg_select_material_type), Toast.LENGTH_LONG).show();
                return false;
            }
        } else {
            if (selectedMaterialName == null) {
                Toast.makeText(getActivity(), getString(R.string.msg_select_material_type), Toast.LENGTH_LONG).show();
                return false;
            }
        }
        if (edtQty.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), getString(R.string.msg_enter_qty), Toast.LENGTH_LONG).show();
            return false;
        }
        if (selectedUnit == null || SELECT_UNIT.equals(selectedUnit)) {
            Toast.makeText(getActivity(), getString(R.string.msg_select_unit), Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void clickListeners() {
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton rb = (RadioButton) group.findViewById(checkedId);
            String materialType = rb.getText() + " " + "Material";
            mGateEntryPresenter.setMaterialType(materialType);
            loadSpinner(materialType);
        });
        spnUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!spnUnit.getSelectedItem().toString().equals(SELECT_UNIT)) {
                    selectedUnit = spnUnit.getSelectedItem().toString();
                    mGateEntryPresenter.setUnit(selectedUnit);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        edtQty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mGateEntryPresenter.setQty(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void loadSpinner(String materialType) {
        showProgress();
        int userID = Integer.parseInt(SPTRaceWizsConstants.getUserId(getActivity()));
        APIService apiService = ApiClients.getRetrofitInstance(false).create(APIService.class);
        Observable<List<Material>> observable = apiService.getMaterialType(getTokenFromSp(getActivity()), CONTENT_TYPE, materialType.trim()).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(new Observer<List<Material>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, e.getMessage());
                if (UNAUTHORISED.equals(e.getMessage())) {
                    //call refresh token
                    getRefreshToken(materialType, userID);
                } else {
                    dismissProgress();
                }
            }

            @Override
            public void onNext(List<Material> materialList) {
                if (materialList != null && materialList.size() > 0) {
                    getListMaterialNameByMaterialTye(materialList);
                }
            }
        });

    }

    void getRefreshToken(String materialType, int userID) {
        APIService apiService = ApiClients.getRetrofitInstance(false).create(APIService.class);
        apiService.refreshToken(userID).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response != null) {
                    ServerResponse refreshTokenResponse = response.body();
                    SPTRaceWizsConstants.saveToken(getActivity(), refreshTokenResponse.getMessage());
                    loadSpinner(materialType);
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

            }
        });
    }

    void getListMaterialNameByMaterialTye(List<Material> materialList) {
        ArrayList<String> materialDisplayNames = new ArrayList<>();
        HashMap<String, Material> nameMaterialMap = new HashMap<>();
        int index = 0;
        String materialName = null;
        for (Material material : materialList) {
            nameMaterialMap.put(material.getMaterialName(), material);
            if (mAllGateEntry != null) {
                if (mAllGateEntry.getMaterial().getMaterialId() == material.getMaterialId()) {
                    SELECT_MATERIAL = material.getMaterialName();
                    selectedMaterialName = material.getMaterialName();
                    mGateEntryPresenter.setMaterialNameId(String.valueOf(material.getMaterialId()));
                    index = materialList.indexOf(material);
                } else {
                    materialDisplayNames.add(material.getMaterialName());
                }
            } else {
                materialDisplayNames.add(material.getMaterialName());
            }
        }
        Log.e("Selected Material ", SELECT_MATERIAL);
        Collections.sort(materialDisplayNames);
        materialDisplayNames.add(index, SELECT_MATERIAL);

        String e = materialDisplayNames.get(materialDisplayNames.size() - 1);
        if (e.equals(SELECT_MATERIAL)) {
            materialDisplayNames.remove(materialDisplayNames.size() - 1);
        }

        dismissProgress();

        ArrayAdapter<String> classAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, materialDisplayNames);
        spnMaterial.setAdapter(classAdapter);
        spnMaterial.setSelection(index);
        spnMaterial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!SELECT_MATERIAL.equalsIgnoreCase(spnMaterial.getSelectedItem().toString())) {
                    selectedMaterialName = "";
                    mMaterial = new Material();
                    selectedMaterialName = spnMaterial.getSelectedItem().toString();
                    mMaterial = nameMaterialMap.get(spnMaterial.getSelectedItem().toString());
                    mGateEntryPresenter.setMaterialNameId(String.valueOf(mMaterial.getMaterialId()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void handleGateEntryFailure(String errorMsg) {

    }

    private void loadMaterialFields() {
        if (mAllGateEntry != null) {
            if (RAW_MATERIAL.equals(mAllGateEntry.getMaterialType())) {
                rbRaw.setChecked(true);
                rbPackage.setChecked(false);
            } else {
                rbRaw.setChecked(false);
                rbPackage.setChecked(false);
            }
            loadSpinner(mAllGateEntry.getMaterialType());
            if (mAllGateEntry.getQuantity() != null)
                edtQty.setText(mAllGateEntry.getQuantity().toString().trim());
            loadUnit();

        }
    }

    private void loadUnit() {
        ArrayList<String> unitList = new ArrayList<>();
        unitList.addAll(Arrays.asList(getResources().getStringArray(R.array.unit_array)));
        int index = 0;
        String unitName = mAllGateEntry.getMaterial().getUnit().getName();
        for (String unit : unitList) {

            if (unitName.equals(unit)) {
                index = unitList.indexOf(unit);
                selectedUnit = unitName;
                break;
            }
        }
        spnUnit.setSelection(index);

    }
}